package upandgo.shared.model.scedule;

import java.util.HashMap;
import java.util.Map;

public enum Color {
	ORANGERED("ORANGERED"),
	GOLD("GOLD"),
	PALETURQUOISE("PALETURQUOISE"),
	SLATEBLUE("SLATEBLUE"),
	LIMEGREEN("LIMEGREEN"),
	GOLDENROD("GOLDENROD");
	
	private static Map<Integer, Color> map = new HashMap<Integer, Color>();
	private String name;  
	
	private Color(String s) {
        name = s;
    }
	
    static {
        for (Color c : Color.values()) {
            map.put(c.ordinal(), c);
        }
    }
    public static Color valueOf(int ordinal) {
        return map.get(ordinal);
    }
    
    public String toString() {
        return this.name;
     }
}
