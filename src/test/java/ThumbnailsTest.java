

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailsTest {

	
	public static void main(String[] args) throws IOException {
		String path = "d:/opt/1.jpg";
		Thumbnails.of(path).size(200, 200).outputQuality(1).toFile(new File("d:\\opt/1111.jpg"));
	}
}
