package exercise;

public class PBV_PBR_Demo {
	public static void main(String[] args) {
      Laddu laddu=new Laddu();
		laddu.size=10;
		PBV pbv=new PBV();
		
		
		System.out.println("Size of laddu before PBV..:"+laddu.size);//10
		new PBV().accept(laddu.size);
		System.out.println("Size of laddu after PBV..:"+laddu.size);//10
		
		
		System.out.println("Size of laddu before PBR..:"+laddu.size);//10
		new PBR().accept(laddu);
		System.out.println("Size of laddu after PBR...:"+laddu.size);//5 
		PBV pvs=new PBV();
	//	pvs.Dem();
	  	World w=new World();
	  	World tempw =w;
	 // tempw=w;
	  	
	  	w.size=1000;
	
		System.out.println(w.size);
		w.size=w.size-100;
		System.out.println(tempw.size);
	}
	
}
class PBV{
	public void accept(int size) {
		size=size-5;
	}
}
class PBR{
	public void accept(Laddu laddu) {
		laddu.size=laddu.size-5;
	}
} 
class Laddu{
	int size; 
//} 


//class PBV
//{
	public void Dem()
	{
	  	int value=100;
	  	
	 	int k=value;
	  	
	  	value=value+value;
	 // 	int k=value;
	  	
	  	System.out.println(value);
	  	System.out.println(k);
	  	
	  	String tem="Cristiano";
	  	
	  	String str=tem;
	  	
	  	tem=tem+"Ronaldo.";
	  	System.out.println(tem);
	  	System.out.println(str);
	  	
	}
}
class World{
	int size=10;
}