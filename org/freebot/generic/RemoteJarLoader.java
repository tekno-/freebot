package org.freebot.generic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class RemoteJarLoader implements Runnable {

    private final Map<String, byte[]> entryMap;
    private final List<URL> urls;
    public RemoteJarLoader() {
        entryMap = new HashMap<>();
        urls = new ArrayList<>();
    }

    private void loadEntries(URL gamepackUrl) {
        try {
            JarInputStream jis = new JarInputStream(gamepackUrl.openStream());
            JarEntry entry;
            while((entry = jis.getNextJarEntry()) != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int read;
                while((read = jis.read(buf, 0, 1024)) > 0) {
                    bos.write(buf, 0, read);
                }
                entryMap.put(entry.getName(), bos.toByteArray());
                bos.close();
            }
            jis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RemoteJarLoader addToLoadQueue(URL url) {
        this.urls.add(url);
        return this;
    }

    /**
     *
     * @return a total map of all jar entries
     */
    public Map<String, byte[]> jarEntries() {
        return entryMap;
    }

    @Override
    public void run() {
        for(URL url : urls) {
            loadEntries(url);
        }
    }
}
