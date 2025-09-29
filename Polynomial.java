import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial
{
	private double[] coefficients;
	private int[] exponents;

	//default
	public Polynomial()
	{
		coefficients = new double[]{0};
		exponents = new int[]{0};
	}

	//create arrays by coefficient numbers array
	public Polynomial(double[] num_coefficients)		//coeffs = {3, 2, 1}
	{
		//count Polynomial
		int count = 0;
		for(int i = 0; i < num_coefficients.length; i++)
		{
			if(num_coefficients[i] != 0)
			{
				count++;
			}
		}

		//zero polynomial
		if(count == 0)
		{
			coefficients = new double[]{0};
			exponents = new int[]{0};
			return;
		}

		//create 2 arrays(coeff & exponent) with counted size
		coefficients = new double[count];
		exponents = new int[count];

		int index = 0;
		for(int i = 0; i < num_coefficients.length; i++)
		{
			if(num_coefficients[i] != 0)
			{
				coefficients[index] = num_coefficients[i];
				exponents[index] = i;
				index++;
			}
		}

	}

	//
	public Polynomial(double[] coefficients, int[] exponents)		//coeffs = {3, 2, 1}, exponents = {1, 2, 3}
	{
		if(coefficients.length != exponents.length)
		{
			throw new IllegalArgumentException("Coefficient and exponent arrays must have same length");
		}

		//count coefficients
		int count = 0;
		for(int i = 0; i < coefficients.length; i++)
        {
        	if(coefficients[i] != 0)
            {
                count++;
            }
        }

		//zero polynomial
		if(count == 0)
        {
            this.coefficients = new double[]{0};
            this.exponents = new int[]{0};
            return;
        }

		//create 2 arrays(coeff & exponent) with counted size
        this.coefficients = new double[count];
        this.exponents = new int[count];
        int index = 0;
        for(int i = 0; i < coefficients.length; i++)
        {
            if(coefficients[i] != 0)
            {
                this.coefficients[index] = coefficients[i];
                this.exponents[index] = exponents[i];
                index++;
            }
        }
	}

	//read file to get Polynomial from a file
	public Polynomial(File file) throws IOException
    {
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine().trim();
        scanner.close();
        
        parsePolynomialString(line);
    }

	//helper function for read file (break down words into array)
	private void parsePolynomialString(String poly)
	{
		// Add + at beginning if it doesn't start with + or -
		if(!poly.startsWith("+") && !poly.startsWith("-"))
		{
			poly = "+" + poly;
		}
		
		// Split by + and - while keeping the operators
		String[] terms = poly.split("(?=[+-])");
		
		// Count valid terms
		int validTerms = 0;
		for(String term : terms)
		{
			if(term.length() > 0)
			{
				validTerms++;
			}
		}
		
		// Arrays to store coefficients and exponents
		double[] tempCoeffs = new double[validTerms];
		int[] tempExps = new int[validTerms];
		int count = 0;
		
		// Parse each term
		for(String term : terms)
		{
			if(term.length() == 0) continue;
			
			term = term.trim();
			double coeff = 0;
			int exp = 0;
			
			if(term.contains("x"))
			{
				String[] parts = term.split("x");
				
				String coeffStr = parts[0];
				if(coeffStr.equals("+") || coeffStr.equals(""))
				{
					coeff = 1;
				}
				else if(coeffStr.equals("-"))
				{
					coeff = -1;
				}
				else
				{
					coeff = Double.parseDouble(coeffStr);
				}
				
				// Find exponent
				if(parts.length == 1)
				{
					exp = 1; // just x means x^1
				}
				else
				{
					// Del ^ and parse
					String expStr = parts[1].replace("^", "");
					exp = Integer.parseInt(expStr);
				}
			}
			else
			{
				// Constant term
				coeff = Double.parseDouble(term);
				exp = 0;
			}
			
			//add non-zero coefficients
			if(coeff != 0)
			{
				tempCoeffs[count] = coeff;
				tempExps[count] = exp;
				count++;
			}
		}
		
		// zero polynomial
		if(count == 0)
		{
			coefficients = new double[]{0};
			exponents = new int[]{0};
		}
		else
		{
			// Create final arrays with counted size
			coefficients = new double[count];
			exponents = new int[count];
			
			for(int i = 0; i < count; i++)
			{
				coefficients[i] = tempCoeffs[i];
				exponents[i] = tempExps[i];
			}
		}
	}

	//add 2 Polynomial together
	public Polynomial add(Polynomial other) 
	{
		int maxDegree = 0;
    
		for(int i = 0; i < this.exponents.length; i++)
		{
			if(this.exponents[i] > maxDegree)
			{
				maxDegree = this.exponents[i];
			}
		}
		for(int i = 0; i < other.exponents.length; i++)
		{
			if(other.exponents[i] > maxDegree)
			{
				maxDegree = other.exponents[i];
			}
		}
		
		double[] thisDense = new double[maxDegree + 1];
		double[] otherDense = new double[maxDegree + 1];
		
		for(int i = 0; i < this.coefficients.length; i++)
		{
			thisDense[this.exponents[i]] = this.coefficients[i];
		}
		
		for(int i = 0; i < other.coefficients.length; i++)
		{
			otherDense[other.exponents[i]] = other.coefficients[i];
		}
		
		double[] resultDense = new double[maxDegree + 1];
		for(int i = 0; i <= maxDegree; i++)
		{
			resultDense[i] = thisDense[i] + otherDense[i];
		}
		
		return new Polynomial(resultDense);
	}
	
	public Polynomial multiply(Polynomial other)
	{
		// Find maximum possible degree
		int maxDegree = 0;
		for(int i = 0; i < this.exponents.length; i++)
		{
			for(int j = 0; j < other.exponents.length; j++)
			{
				int newExp = this.exponents[i] + other.exponents[j];
				if(newExp > maxDegree)
				{
					maxDegree = newExp;
				}
			}
		}
		
		// Create result array
		double[] resultDense = new double[maxDegree + 1];
		
		// Multiply each term
		for(int i = 0; i < this.coefficients.length; i++)
		{
			for(int j = 0; j < other.coefficients.length; j++)
			{
				int newExp = this.exponents[i] + other.exponents[j];
				double newCoeff = this.coefficients[i] * other.coefficients[j];
				resultDense[newExp] += newCoeff;  // Combines like terms
			}
		}
		
		return new Polynomial(resultDense);
	}

	//evaluate the final value by given number x
	public double evaluate(double x)
	{
		double value = 0.0;
		for(int i = 0; i < coefficients.length; i++)
		{
			value += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return value;
	}
	

	public boolean hasRoot(double num)
	{
		return Math.abs(evaluate(num)) < 1e-10;  // Tolerance for rounding errors
	}

	public void saveToFile(String filename) throws IOException
	{
		PrintWriter writer = new PrintWriter(filename);
		writer.print(toString());
		writer.close();
	}

	public String toString()
	{
		if(coefficients.length == 0 || (coefficients.length == 1 && coefficients[0] == 0))
		{
			return "0";
		}
		
		String result = "";
		boolean first = true;
		
		for(int i = 0; i < coefficients.length; i++)
		{
			double coeff = coefficients[i];
			int exp = exponents[i];
			
			if(coeff == 0) continue;
			
			// Add sign
			if(!first)
			{
				if(coeff > 0)
					result += "+";
				else
					result += "-";
			}
			else
			{
				if(coeff < 0)
				{
					result += "-";
				}
				first = false;
			}
			
			// Use absolute value for the coefficient
			coeff = Math.abs(coeff);
			
			// Add coefficient
			if(exp == 0)  // Constant term
			{
				if(coeff == (int)coeff)
					result += (int)coeff;
				else
					result += coeff;
			}
			else  // Term with x
			{
				if(coeff != 1)
				{
					if(coeff == (int)coeff)
						result += (int)coeff;
					else
						result += coeff;
				}
				
				result += "x";
				if(exp > 1)
					result += exp;
			}
		}
		
		return result;
	}
}