package com.example.chess.core.icon;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import com.example.chess.core.model.Side;

public class IconHandler {
	private static Path path;	
	private static ArrayList<Image> images = new ArrayList<>();
	
	static {		
		try {
			URL location = IconHandler.class.getProtectionDomain().getCodeSource().getLocation();
			path = Paths.get(location.toURI()).resolve("../com.example.chess.core/images");
		} 
		
		catch (URISyntaxException exception) {
			exception.printStackTrace();
		}
	}
	
	public static Image getIcon(String pieceName, Side side) {
        String imageName = (side == Side.WHITE ? "white" : "black") + pieceName + ".png";
        Image image = new Image(Display.getDefault(), path.resolve(imageName).toString());
        images.add(image);
        return image;
    }

    public static Image getBlankIcon() {
        Image image = new Image(Display.getDefault(), path.resolve("blank.png").toString());
        images.add(image);
        return image;
    }

    public static void disposeImages() {
        for(int i = 0; i < images.size(); ++i) {
            if(images.get(i) != null && !images.get(i).isDisposed()) {
                images.get(i).dispose();
            }
        }
        images.clear();
    }
	
}

