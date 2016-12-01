package cn.com.oasis.base.net.okhttpnet.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.oasis.base.net.okhttpnet.request.PostRequest;
import cn.com.oasis.base.net.okhttpnet.request.RequestCall;
import okhttp3.MediaType;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/2 下午12:07
 */

public class PostBuilder extends ReqestBuilder<PostBuilder> implements IHasParamsable{
    private MediaType mediaType;
    protected static MediaType MEDIA_TYPE_DETUALT = MediaType.parse("application/json");
    private List<FileInput> files = new ArrayList<>();

    public PostBuilder() {
        this.mediaType = MEDIA_TYPE_DETUALT;
    }

    @Override
    public RequestCall build() {
        return new PostRequest(url, tag, params, headers, mediaType,files, id).build();
    }

    public PostBuilder mediaType(MediaType mediaType){
        this.mediaType = mediaType;
        return this;
    }

    public PostBuilder addFile(String name, String filename, File file){
        files.add(new FileInput(name, filename, file));
        return this;
    }

    public PostBuilder addFiles(String name, List<File> filesInput){
        if (filesInput == null || filesInput.isEmpty()) return this;
        else {
            for (File file:filesInput){
                files.add(new FileInput(name, name, file));
            }
        }

        return this;
    }

    @Override
    public PostBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostBuilder params(String key, String val) {
        if (params == null){
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public static class FileInput{
        public String key;
        public String fileName;
        public File file;

        public FileInput(String key, String fileName, File file) {
            this.key = key;
            this.fileName = fileName;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", fileName='" + fileName + '\'' +
                    ", file=" + file +
                    '}';
        }
    }
}
