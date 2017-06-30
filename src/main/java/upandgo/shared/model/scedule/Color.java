package upandgo.shared.model.scedule;

import java.util.HashMap;
import java.util.Map;

public enum Color {
	ORANGERED("ORANGERED", "#FF4500"),
	GOLD("GOLD", "#FFD700"),
	PALETURQUOISE("PALETURQUOISE", "#AFEEEE"),
	SLATEBLUE("SLATEBLUE", "#6A5ACD"),
	LIMEGREEN("LIMEGREEN", "#32CD32"),
	GOLDENROD("GOLDENROD", "#DAA520"),
	BLUEVIOLET("BLUEVIOLET", "#8A2BE2"),
	BROWN("BROWN", "#A52A2A"),
	CORAL("CORAL", "#FF7F50"),
	CHARTREUSE("CHARTREUSE", "#7FFF00"),
	CORNFLOWERBLUE("CORNFLOWERBLUE", "#6495ED"),
	DARKORANGE("DARKORANGE", "#FF8C00"),
	DARKSLATEGRAY("DARKSLATEGRAY", "#2F4F4F"),
	FUCHSIA("FUCHSIA", "#FF00FF"),
	OLIVEDRAB("OLIVEDRAB", "#6B8E23"),
	SEAGREEN("SEAGREEN", "#2E8B57"),
	TURQUOISE("TURQUOISE", "#40E0D0"),
	NAVY("NAVY", "#000080"),
	CRIMSON("CRIMSON", "#DC143C");
	
	
	
	
	private static Map<Integer, Color> map = new HashMap<Integer, Color>();
	private String name;  
	private String hex;
	
	private Color(String s, String h) {
        name = s;
        hex = h;
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
    
    public String getHex(){
    	return this.hex;
    }
}
