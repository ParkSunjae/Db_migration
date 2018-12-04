package kr.co.kyobo.vora.util;

import kr.co.kyobo.vora.model.database.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TagsUtil {
    private static final Pattern pattern = Pattern.compile("#[^\\s#]+");
    public static List<Tags> parseTags(String str){
        Matcher matcher = pattern.matcher(str);
        HashSet<Tags> tags = new HashSet<>();
        while(matcher.find()){
            Tags tag = new Tags();
            tag.setTag(matcher.group().replace("#","").trim());
            tags.add(tag);
        }
        return tags.stream().collect(Collectors.toList());
    }
}
