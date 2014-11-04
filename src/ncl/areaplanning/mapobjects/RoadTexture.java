package ncl.areaplanning.mapobjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RoadTexture
{
	/*
		 * Road object indicates the map key for coresponsing image file. Letter
		 * indicates the Borders that are in the image file. T - Top B - Bottom L -
		 * Left R - Right C - Full Crossroad I.E. LB would look something like this
		 * |_ , RB - _| .
		 */
	public static final Map<String, String> textureMap;
	static {
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("Basic", "road-tb-map.png"); 
		tempMap.put("TB", "road-tb-map.png"); 
		tempMap.put("LR", "road-lr-map.png");
		tempMap.put("RB", "road-rb-map.png");
		tempMap.put("RT", "road-rt-map.png");
		tempMap.put("LB", "road-lb-map.png");
		tempMap.put("LT", "road-lt-map.png");
		tempMap.put("T", "road-t-map.png");
		tempMap.put("B", "road-b-map.png");
		tempMap.put("R", "road-r-map.png");
		tempMap.put("L", "road-l-map.png");
		tempMap.put("C", "road-c-map.png");
		textureMap = Collections.unmodifiableMap(tempMap);
	}

}
