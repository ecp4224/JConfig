package me.eddiep.jconfig.system;

import me.eddiep.jconfig.system.annotations.Operation;

import java.io.File;

public interface Config {

    @Operation(type = OperationType.SAVE)
    public void save(String file);

    @Operation(type = OperationType.SAVE)
    public void save(File file);

    @Operation(type = OperationType.LOAD)
    public void load(String file);

    @Operation(type = OperationType.LOAD)
    public void load(File file);
}
