import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		
		String[] ary = "force?No!".split("(?<=[\\.?!])");
		System.out.println(Arrays.asList(ary));
	}

}
