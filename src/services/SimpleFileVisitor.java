package services;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public interface SimpleFileVisitor<Path> extends FileVisitor<Path>{

	FileVisitResult postVisitDirectory(Path dir, IOException exc);
	FileVisitResult visitFileFailed(Path file, IOException exc);
	FileVisitResult visitFile(Path file, BasicFileAttributes attrs);
	
}
