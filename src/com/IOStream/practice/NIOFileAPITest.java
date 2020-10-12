package com.IOStream.practice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class NIOFileAPITest {
	private static String HOME = System.getProperty("user.home");
	private static String PLAY_WITH_NIO = "TempPlayGround2";

	@Test
	public void firstTest() throws IOException {
		Path homePath = Paths.get(HOME);
		System.out.println("Path is : " + System.getProperty("user.home"));
		Assert.assertTrue(Files.exists(homePath));
		Path playPath = Paths.get(HOME + "/Desktop/Capgemini stuff/" + PLAY_WITH_NIO);
		if (Files.exists(playPath))
			FileUtils.deleteDirectory(playPath.toFile());
		Assert.assertTrue(Files.notExists(playPath));
		Files.createDirectory(playPath);
		Assert.assertTrue(Files.exists(playPath));

		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(playPath + "/temp" + cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try {
				Files.createFile(tempFile);
			} catch (IOException e) {
				// TODO: handle exception
				Assert.assertTrue(Files.exists(tempFile));
			}
		});

		Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
		System.out.println();
		Files.newDirectoryStream(playPath).forEach(System.out::println);
		System.out.println();
		Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
				.forEach(System.out::println);
	}
}
