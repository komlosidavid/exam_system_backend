package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.GetAllTestsFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllTestsFilterRequest {
    private GetAllTestsFilter filter;
}
