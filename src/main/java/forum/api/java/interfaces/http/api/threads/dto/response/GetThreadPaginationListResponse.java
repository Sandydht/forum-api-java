package forum.api.java.interfaces.http.api.threads.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetThreadPaginationListResponse {
    List<GetThreadDetailResponse> data;
    int page;
    int size;
    long totalElements;
    int totalPages;
}
