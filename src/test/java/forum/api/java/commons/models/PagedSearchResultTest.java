package forum.api.java.commons.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("PagedSearchResult")
public class PagedSearchResultTest {
    @Test
    @DisplayName("should save data and metadata correctly")
    public void shouldSaveDataAndMetadataCorrectly() {
        List<String> dummyData = List.of("Data 1", "Data 2", "Data 3", "Data 4", "Data 5", "Data 6", "Data 7", "Data 8", "Data 9", "Data 10", "Data 11");
        int page = 0;
        int size = 10;
        long totalElements = 25;

        PagedSearchResult<String> result = new PagedSearchResult<>(dummyData, page, size, totalElements);

        Assertions.assertEquals(11, result.getData().size());
        Assertions.assertEquals(25, result.getTotalElements());
        Assertions.assertEquals(3, result.getTotalPages()); // 25 data / 10 per page = 3 pages
    }

    @Test
    @DisplayName("should calculate totalPages correctly")
    public void shouldCalculateTotalPagesCorrectly() {
        PagedSearchResult<Integer> result1 = new PagedSearchResult<>(List.of(1), 0, 10, 25);
        Assertions.assertEquals(3, result1.getTotalPages());

        PagedSearchResult<Integer> result2 = new PagedSearchResult<>(List.of(1), 0, 10, 20);
        Assertions.assertEquals(2, result2.getTotalPages());

        PagedSearchResult<Integer> result3 = new PagedSearchResult<>(List.of(1), 0, 10, 5);
        Assertions.assertEquals(1, result3.getTotalPages());

        PagedSearchResult<Integer> result4 = new PagedSearchResult<>(List.of(1), 0, 10, 0);
        Assertions.assertEquals(0, result4.getTotalPages());
    }
}
