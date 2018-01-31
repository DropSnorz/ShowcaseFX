package com.dropsnorz.showcasefx.utils;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

public class BoundsUtils {
	
	public static Point2D getCenter(Bounds bounds) {
		
		double centerX = bounds.getMinX() + (bounds.getWidth() / 2);
		double centerY = bounds.getMinY() + (bounds.getHeight() / 2);
		
		return new Point2D(centerX, centerY);
		
	}
	

}
