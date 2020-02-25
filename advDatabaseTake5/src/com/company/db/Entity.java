package com.company.db;

import java.nio.file.Path;

public interface Entity {
    static Path getPath() {
        return null;
    }

    Path getPathWithId();

    String toPrettyString();
}
