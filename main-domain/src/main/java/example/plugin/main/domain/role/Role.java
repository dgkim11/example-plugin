package example.plugin.main.domain.role;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Role {
    private String id;
    private String name;
    private String nameBundleKey;
    private String description;
}
