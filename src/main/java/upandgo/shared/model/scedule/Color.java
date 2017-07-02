package upandgo.shared.model.scedule;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("boxing")
public enum Color {
	GOLD("GOLD", "#FFD700"),
	PALETURQUOISE("PALETURQUOISE", "#0dc19d"),
	SLATEBLUE("SLATEBLUE", "#6A5ACD"),
	BLUEVIOLET("BLUEVIOLET", "#8A2BE2"),
	BROWN("BROWN", "#A52A2A"),
	CORAL("CORAL", "#FF7F50"),
	CHARTREUSE("CHARTREUSE", "#7FFF00"),
	CORNFLOWERBLUE("CORNFLOWERBLUE", "#6495ED"),
	DARKORANGE("DARKORANGE", "#FF8C00"),
	SEAGREEN("SEAGREEN", "#2E8B57"),
	DARKSLATEGRAY("DARKSLATEGRAY", "#2F4F4F"),
	GOLDENROD("GOLDENROD", "#DAA520"),
	TURQUOISE("TURQUOISE", "#40E0D0"),
	NAVY("NAVY", "#000080"),
	FUCHSIA("FUCHSIA", "#FF00FF"),
	CRIMSON("CRIMSON", "#DC143C"),
	LIMEGREEN("LIMEGREEN", "#32CD32"),
	ORANGERED("ORANGERED", "#FF4500"),
	OLIVEDRAB("OLIVEDRAB", "#6B8E23");
	
	
	
	
	@SuppressWarnings("unused")
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
    
    @Override
	public String toString() {
        return this.name;
     }
    
    public String getHex(){
    	return this.hex;
    }
}
