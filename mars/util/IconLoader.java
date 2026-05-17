package mars.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import com.formdev.flatlaf.util.UIScale;

/**
 * Utility class for loading DPI-aware icons.
 */
public class IconLoader {
    /**
     * Loads an icon from the given URL and scales it to match the current display
     * DPI scaling factor (HiDPI support). Falls back to the raw 1x image if
     * scaling is not needed or fails for any reason.
     */
    public static ImageIcon loadIcon(URL url) {
        if (url == null)
            return null;
        Image img = Toolkit.getDefaultToolkit().getImage(url);
        int scaleFactor = UIScale.scale(1);
        if (scaleFactor <= 1)
            return new ImageIcon(img);

        ImageIcon probe = new ImageIcon(img);
        int w = probe.getIconWidth();
        int h = probe.getIconHeight();
        if (w <= 0 || h <= 0)
            return probe;

        Image scaledImg = img.getScaledInstance(
                UIScale.scale(w), UIScale.scale(h), Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    /**
     * Loads an icon from the given path and scales it to match the current display
     * DPI scaling factor (HiDPI support). Falls back to the raw 1x image if
     * scaling is not needed or fails for any reason.
     */
    public static ImageIcon loadIcon(String path) {
        java.net.URL url = IconLoader.class.getClassLoader().getResource(path);
        if (url == null)
            return new ImageIcon(path);
        return loadIcon(url);
    }
}
