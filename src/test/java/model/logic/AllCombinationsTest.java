package model.logic;
/**
 * @author kobybs
 * @since 29-12-16
 */

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

@SuppressWarnings({"boxing","static-method"})
public class AllCombinationsTest {
	@Test
	public void test() {
		
		ArrayList<Integer> main = new ArrayList<>();
		ArrayList<Integer> max = new ArrayList<>();
		
		main.add(0); max.add(3);
		main.add(0); max.add(2);
		main.add(0); max.add(3);
		main.add(0); max.add(4);
		main.add(0); max.add(3);
		main.add(0); max.add(2);
		main.add(0); max.add(3);
		main.add(0); max.add(4);
		main.add(0); max.add(3);
		main.add(0); max.add(2);
		main.add(0); max.add(3);
		main.add(0); max.add(4);
		
		Random rand = new Random();

		int  n = rand.nextInt(50);
		
		for (int last = main.size() - 1, msb;;) {
			//if()
			n = rand.nextInt(170);
			if(n == 0 )
				System.out.println(main);
			
			main.set(last, main.get(last) + 1);
			if (main.get(last) > max.get(last)) {
				msb = last - 1;
				// find lowest index which is not yet maxed
				for (; msb >= 0; --msb)
					if (main.get(msb) < max.get(msb))
						break;
				// if every index is max than we made all combinations
				if (msb < 0)
					break;
				// increase msb and zero everything to its right
				main.set(msb, main.get(msb) + 1);
				for (int ¢ = msb + 1; ¢ <= last; ++¢)
					main.set(¢, 0);
			}
		}
		
	}


}
