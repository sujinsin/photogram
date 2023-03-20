package com.cos.photogramstart.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class ImageUtil {

	// 2mb
	private static final int maxSize = 2 * 1024 * 1024;

	// 가로 세로 중 가장 작은쪽을 1200 으로 제한
	private static final int targetSize = 2048;

	// 클라이언트로부터 받아온 file sourceFile을 리사이징
	public static byte[] resize(byte[] sourceFileBytes, String fileNames) throws IOException {

		float rimitRote = 0.9f;
		// png,jpg 등의 확장자를 추출
		String extension = FilenameUtils.getExtension(fileNames);

		BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(sourceFileBytes));
		BufferedImage targetImage = resizeImage(sourceImage, targetSize);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(targetImage, extension, os);

		while (os.size() > maxSize && rimitRote > 0.5f) {
			os.reset();
			ImageIO.write(targetImage, extension, os);
			rimitRote -= 0.3f;
			targetImage = resizeImage(targetImage, targetSize - 400);
		}
		return os.toByteArray();
	}

	private static BufferedImage resizeImage(BufferedImage sourceImage, int targetSize) {
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();

		double ratio = (double) width / height;

		if (width > height) {
			height = targetSize;
			width = (int) (height * ratio);
		} else {
			width = targetSize;
			height = (int) (width / ratio);
		}

		BufferedImage resizedImage = new BufferedImage(width, height, sourceImage.getType());
		Graphics2D g2 = resizedImage.createGraphics();

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_RESOLUTION_VARIANT,
				RenderingHints.VALUE_RESOLUTION_VARIANT_DPI_FIT);
		g2.setRenderingHints(hints);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		g2.drawImage(sourceImage, 0, 0, width, height, null);
		g2.dispose();

		return resizedImage;
	}

}