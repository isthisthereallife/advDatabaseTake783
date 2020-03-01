package com.company.db;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface GetFromPath {
    void checkPath(Path path);

    void verifyDirectoryIntegrity(Path path);

    File[] getFilesFromPath(Path path);

    List<String> makeListFromTxt(Path path);
}
