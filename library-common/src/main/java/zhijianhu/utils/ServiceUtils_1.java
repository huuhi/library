package zhijianhu.utils;

import zhijianhu.vo.TagsVO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServiceUtils {
    /**
     * 通用映射构建方法
     * @param sourceList 源数据列表
     * @param idExtractor 从源对象中提取ID的函数
     * @param resultFetcher 根据ID集合获取结果对象的函数
     * @param keyMapper 从结果对象中提取key的函数
     * @param valueMapper 从结果对象中提取value的函数
     * @return Map<Key类型, Value类型>
     */
    public static <T, R, K, V> Map<K, V> buildEntityMap(
            Collection<T> sourceList,
            Function<T, K> idExtractor,
            Function<Set<K>, List<R>> resultFetcher,
            Function<R, K> keyMapper,
            Function<R, V> valueMapper) {

        Set<K> ids = sourceList.stream()
                .map(idExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }

        return resultFetcher.apply(ids).stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }
      public static Map<Integer, List<TagsVO>> buildPostTagMap(
            Collection<Integer> postIds,
            Function<Collection<Integer>, Map<Integer, List<Integer>>> tagRelationFetcher,
            Function<Collection<Integer>, Map<Integer, TagsVO>> tagDetailFetcher) {

            // 1. 获取帖子-标签关系映射
            Map<Integer, List<Integer>> postTagRelations = tagRelationFetcher.apply(postIds);

            // 2. 收集所有标签ID
            Set<Integer> allTagIds = postTagRelations.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());

            if (allTagIds.isEmpty()) {
                return Collections.emptyMap();
            }

            // 3. 批量获取标签详情
            Map<Integer, TagsVO> tagDetailMap = tagDetailFetcher.apply(allTagIds);

            // 4. 构建最终映射
            return postTagRelations.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(tagDetailMap::get)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList())
                    ));
        }
}