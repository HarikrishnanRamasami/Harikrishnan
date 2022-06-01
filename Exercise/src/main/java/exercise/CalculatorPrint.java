package exercise;

public class CalculatorPrint
{
	public static void main(String[] args) 
	{
		Calculator calculator= new Calculator();
		Calculator.Add(10, 20);
		calculator.Dis(12, 6);
		calculator.display(30);

	//    calculator.Get(20,20);
		String result=calculator.Get(20,20);
		calculator.Get(30,40);
		
		System.out.println("Return value is  : "+result);
		calculator.Result(new int[] {20,30,50,67});
	}

}
