package task3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import task3.models.generated.Tag;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TagEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "_key")
    private String key;
    
    @Column(name = "value")
    private String value;
    
    public static TagEntity convertFromXMLTag(Tag tag) {
        return new TagEntity().setKey(tag.getK())
                .setValue(tag.getV());
    }
}
