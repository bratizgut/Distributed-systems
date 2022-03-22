package task3.entity;

import java.util.Set;
import static java.util.stream.Collectors.toSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import task3.models.generated.Way;

@Entity
@Table(name = "ways")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class WayEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "way_tag", joinColumns = {
        @JoinColumn(name = "way_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "id")
    })
    private Set<TagEntity> tags;

    @Column(name = "_user")
    private String user;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "version")
    private Long version;

    @Column(name = "changeset")
    private Long changeset;
    
//    private XMLGregorianCalendar timestamp;
    
    public static WayEntity convertFromXMLWay(Way way) {
        return new WayEntity().setId(way.getId().longValue())
                .setUser(way.getUser()).setUid(way.getUid().longValue())
                .setVisible(way.isVisible()).setVersion(way.getVersion().longValue())
                .setChangeset(way.getChangeset().longValue())
                .setTags(way.getTag()
                        .stream()
                        .map(TagEntity::convertFromXMLTag)
                        .collect(toSet()));
    }
}
