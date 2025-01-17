/*
 * Copyright 2020, 2021, 2022 Clément "carlodrift" Raynaud, Lucas "Lucas_Cdry" Cadiry and contributors
 *
 * This file is part of Skoice.
 *
 * Skoice is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Skoice is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Skoice.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.clementraynaud.skoice.storage;

import net.clementraynaud.skoice.Skoice;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public abstract class FileStorage {

    private static final String FILE_HEADER = "Do not edit this file manually, otherwise Skoice could stop working properly.";
    protected final YamlConfiguration yaml = new YamlConfiguration();
    protected final Skoice plugin;
    private final String fileName;
    private File file;

    protected FileStorage(Skoice plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }

    public void load() {
        this.file = new java.io.File(this.plugin.getDataFolder() + java.io.File.separator + this.fileName + ".yml");
        try {
            if (this.file.exists() || this.file.createNewFile()) {
                this.yaml.load(this.file);
                this.saveFile();
            }
        } catch (IOException | InvalidConfigurationException ignored) {
        }
    }

    public FileConfiguration getFile() {
        return this.yaml;
    }

    @SuppressWarnings("deprecation")
    public void saveFile() {
        try {
            this.yaml.options().setHeader(Collections.singletonList(FileStorage.FILE_HEADER));
        } catch (NoSuchMethodError e) {
            this.yaml.options().header(FileStorage.FILE_HEADER);
        }
        try {
            this.yaml.save(this.file);
        } catch (IOException ignored) {
        }
    }
}
