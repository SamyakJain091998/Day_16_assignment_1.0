package com.IOStream.practice;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class JavaWatchServiceExample {

	public enum Kind {
		ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY
	}

	private final WatchService watcher;
	private final Map<WatchKey, Path> dirWatchers;

	JavaWatchServiceExample(Path dir) throws IOException {
		// TODO Auto-generated constructor stub
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dirWatchers = new HashMap<>();
		scanAndRegisterDirectories(dir);
	}

	private void registerDirWatchers(Path dir) throws IOException {
		// TODO Auto-generated method stub
		WatchKey key = dir.register(watcher, JavaWatchServiceExample.Kind.ENTRY_CREATE,
				JavaWatchServiceExample.Kind.ENTRY_MODIFY, JavaWatchServiceExample.Kind.ENTRY_DELETE);
		dirWatchers.put(key, dir);
	}

	private void scanAndRegisterDirectories(final Path start) throws IOException {
		// TODO Auto-generated method stub
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				registerDirWatchers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processEvents() {
		// TODO Auto-generated method stub
		while (true) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				// TODO: handle exception
				return;
			}
			Path dir = dirWatchers.get(key);
			if (dir == null) {
				continue;
			}
			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				Path name = ((WatchEvent<Path>) event).context();
				Path child = dir.resolve(name);
				System.out.format("%s: %s\n", event.kind().name(), child);

				if (kind == ENTRY_CREATE) {
					try {
						if (Files.isDirectory(child))
							scanAndRegisterDirectories(child);
					} catch (IOException e) {
						// TODO: handle exception
					}
				} else if (kind.equals(ENTRY_DELETE)) {
					if (Files.isDirectory(child))
						dirWatchers.remove(key);
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				dirWatchers.remove(key);
				if (dirWatchers.isEmpty())
					break;
			}
		}
	}

}
