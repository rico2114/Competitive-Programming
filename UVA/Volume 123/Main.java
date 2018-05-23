import java.util.*;
import java.io.*;
import java.util.*;

public class Main {
	private static StringBuilder builder = new StringBuilder();
	private static Stack<Function> stack = new Stack<Function>();
 	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 	private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));

	public static Template parse(final String formulaString) {
		char [] array = formulaString.toCharArray();
		Function lastFunction = null;
		for (int j = 0; j < array.length; ++ j) {
			char currChar = array[j];
			if (currChar != '(' && currChar != ',' && currChar != ')') {
				builder.append(currChar);
			} else if (currChar == '(') {
				Function function = new Function(builder.toString());
				if (stack.size() != 0) {
					((Function)stack.peek()).addParameter(function);
				}
				stack.push(function);
				builder.setLength(0);
			} else if (currChar == ',' || currChar == ')') {
				if (builder.length() > 0) {
					if (Character.isLowerCase(builder.charAt(0))) {
						stack.peek().addParameter(new Constant(builder.toString()));
					} else {
						stack.peek().addParameter(new Variable(builder.toString()));
					}
					builder.setLength(0);
				}
				if (currChar == ')') {
					lastFunction = stack.peek();
					stack.pop();
				}
			}
		}
		String name = builder.toString();
		builder.setLength(0); 
		if (lastFunction == null) {
			if (Character.isLowerCase(name.charAt(0))) {
				return new Constant(name);
			} else {
				return new Variable(name);
			}
		} else {
			return lastFunction;
		}
	}

	public static Template substituteFormula(Template formula, Pair<Template, Template> substitute) {
		Template variable = substitute.getLeft();
		Template changeTo = substitute.getRight();
		if (formula.containsVariable(variable)) {
			if (formula.isFunction()) {
				formula.substitute(variable, changeTo);
			} else if (formula.isVariable()) {
				return changeTo;
			}
		}
		return formula;
	}

	public static void substitute(final ArrayList<Pair<Template, Template>> list, Pair<Template, Template> substitution) {
		Template variable = substitution.getLeft();
		Template changeTo = substitution.getRight();

		for (int i = 0; i < list.size(); ++ i) {
			Template left = list.get(i).getLeft();
			Template right = list.get(i).getRight();

			if (left.containsVariable(variable)) {
				if (left.isFunction()) {
					((Function) left).substitute(variable, changeTo);
				} else if (left.isVariable()) {
					left = changeTo;
				}

				list.set(i, new Pair<Template, Template>(left, right));
			}

			if (right.containsVariable(variable)) {
				if (right.isFunction()) {
					((Function) right).substitute(variable, changeTo);
				} else if (right.isVariable()) {
					right = changeTo;
				}
				list.set(i, new Pair<Template, Template>(left, right));
			}
		}
	}

	public static boolean unify(final ArrayList<Pair<Template, Template>> universe, final ArrayList<Pair<Template, Template>> theta, final HashMap<String, String> transformations) {
		while (universe.size() != 0) {
			Pair<Template, Template> pair = universe.get(universe.size() - 1);
			universe.remove(universe.size() - 1);

			Template l = pair.getLeft();
			Template r = pair.getRight();

			if (l.getRepresentation().equals(r.getRepresentation())) {
				continue;
			}

			if (l.isFunction() && r.isFunction()) {
				if (!l.getName().equals(r.getName())) {
					return false;
				}
				Function ll = ((Function) l);
				Function rr = ((Function) r);

				if (ll.getParameterCuantity() != rr.getParameterCuantity()) {
					return false;
				}
				for (int i = 0; i < ll.getParameterCuantity(); ++ i) {
					universe.add(0, new Pair<Template, Template>(ll.getParameter(i), rr.getParameter(i)));
				}
				continue;
			}

			if (l.isVariable() || r.isVariable()) {
				Template variable = null;
				Template term = null;
				if (l.isVariable()) {
					variable = l;
					term = r;
				} else {
					variable = r;
					term = l;
				}

				if (term.containsVariable(variable)) {
					return false;
				}

				if (transformations.containsKey(variable.getRepresentation())) {
					if (!transformations.get(variable.getRepresentation()).equals(term.getRepresentation())) {
						return false;
					}
				}
				Pair<Template, Template> substitution = new Pair<>(variable, term);

				transformations.put(variable.getRepresentation(), term.getRepresentation());
				substitute(universe, substitution);
				substitute(theta, substitution);

				theta.add(substitution);
				continue;
			}
			return false;
		}		
		return true;
	}
	
	public static boolean unifyPair(final String caseName, Template formula1, Template formula2, ArrayList<Pair<Template, Template>> theta, HashMap<String, String> transformations) {
		// Apply transformations
		for (Pair<Template, Template> theta_i : theta) {
			formula1 = substituteFormula(formula1, theta_i);
			formula2 = substituteFormula(formula2, theta_i);
		}
		// Calculate and apply MGU
		final ArrayList<Pair<Template, Template>> universe = new ArrayList<>();
		universe.add(new Pair<Template, Template>(formula1, formula2));
		if (!unify(universe, theta, transformations)) {
			writer.write(caseName + " is a Starflyer agent\n");
			return false;
		}
		return true;
	}

	public static void main(final String [] args) throws Exception {
		String [] line = reader.readLine().split(" ");
		while (!line[0].equals("END")) {
			final ArrayList<Pair<Template, Template>> theta = new ArrayList<>();
			final HashMap<String, String> transformations = new HashMap<>();
			// Data tokenizer
			int size = Integer.parseInt(line[1]);
			String testName = line[0];
			// Parse the first formula to keep parsing from 1 element at time
			boolean success = true;
			String formula1 = reader.readLine();
			Template formula1Object = parse(formula1);
			Template lastFormula = formula1Object;
			Template formula2Object = null;
			// Keep parsing from 1 element at time
			for (int i = 1; i < size; ++ i) {
				String formula2 = reader.readLine();
				if (success) {
					formula2Object = parse(formula2);

					if (!unifyPair(testName, lastFormula, formula2Object, theta, transformations)) {
						success = false;
					}

					lastFormula = formula2Object;
				}
			}
			if (success) {
				writer.write("analysis inconclusive on " + testName + "\n");
			}

			line = reader.readLine().split(" ");
		}
		writer.close();
		reader.close();
	}


	static class Pair<K, V> {
		private K left;
		private V right;

		public Pair(final K left, final V right) {
			this.left = left;
			this.right = right;
		}

		public K getLeft() {
			return left;
		}

		public V getRight() {
			return right;
		}
	}

	static abstract class Template {

		public abstract boolean isFunction();
		public abstract boolean isConstant();
		public abstract boolean isVariable();
		public abstract String getRepresentation();
		public abstract String getName();
		public abstract boolean containsVariable(Template variable);
		public abstract void substitute(Template variable, Template changeTo);

	}

	static class Function extends Template {

		private String name;
		public String cachedStr = null;
		private ArrayList<Template> parameters = new ArrayList<>();

		public Function(final String name) {
			this.name = name;
		}

		public void addParameter(Template term) {
			if (term.hashCode() == this.hashCode()) {
				return;
			}
			parameters.add(term);
		}

		public Template getParameter(int index) {
			return parameters.get(index);
		}
	
		public int getParameterCuantity() {
			return parameters.size();
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getRepresentation() {
			if (cachedStr != null) {
				return cachedStr;
			}
			String output = getName() + "(";
			for (int i = 0; i < getParameterCuantity(); ++ i) {
				if (hashCode() == getParameter(i).hashCode()) {
					continue;
				}
				output += getParameter(i).getRepresentation();

				if (i != getParameterCuantity() - 1) {
					output += ",";
				}
			}

			output += ")";
			cachedStr = output;
			return output;
		}

		@Override
		public boolean containsVariable(Template variable) {
			return getRepresentation().contains(variable.getRepresentation());
		}

		@Override
		public void substitute(Template variable, Template changeTo) {
			cachedStr = null;
			for (int i = 0; i < getParameterCuantity(); ++ i) {
				if (getParameter(i).isFunction() && getParameter(i).containsVariable(variable)) {
					if (getParameter(i).hashCode() == changeTo.hashCode()) {
						continue;
					}	
					((Function) getParameter(i)).substitute(variable, changeTo);
					continue;
				}


				if (getParameter(i).isVariable() && getParameter(i).containsVariable(variable)) {
					parameters.set(i, changeTo);
				}
			}
		}

		@Override
		public boolean isFunction() {
			return true;
		}

		@Override
		public boolean isVariable() {
			return false;
		}

		@Override
		public boolean isConstant() {
			return false;
		}
	}

	static class Variable extends Template {
		private String name;

		public Variable(final String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public boolean containsVariable(Template variable) {
			return getRepresentation().equals(variable.getRepresentation());
		}

		@Override
		public void substitute(Template variable, Template changeTo) {
			// Nothing here
		}

		@Override
		public boolean isFunction() {
			return false;
		}

		@Override
		public boolean isVariable() {
			return true;
		}

		@Override
		public boolean isConstant() {
			return false;
		}

		@Override
		public String getRepresentation() {
			return getName();
		}
	}

	static class Constant extends Template {
		private String name;

		public Constant(final String name) {
			this.name = name;
		}
		@Override
		public String getName() {
			return name;
		}

		@Override
		public boolean containsVariable(Template variable) {
			return false;
		}

		@Override
		public void substitute(Template variable, Template changeTo) {
			// Nothing here
		}

		@Override
		public boolean isFunction() {
			return false;
		}

		@Override
		public boolean isVariable() {
			return false;
		}

		@Override
		public boolean isConstant() {
			return true;
		}

		@Override
		public String getRepresentation() {
			return getName();
		}
	}

}
