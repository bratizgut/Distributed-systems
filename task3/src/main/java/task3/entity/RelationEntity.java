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

import task3.models.generated.Relation;

@Entity
@Table(name = "relations")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class RelationEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "relation_tag", joinColumns = {
        @JoinColumn(name = "relation_id", referencedColumnName = "id")
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

//    protected XMLGregorianCalendar timestamp;
    public static RelationEntity convertFromXMRelation(Relation relation) {
        return new RelationEntity().setId(relation.getId().longValue())
                .setUser(relation.getUser()).setUid(relation.getUid().longValue())
                .setVisible(relation.isVisible()).setVersion(relation.getVersion().longValue())
                .setChangeset(relation.getChangeset().longValue())
                .setTags(relation.getTag()
                        .stream()
                        .map(TagEntity::convertFromXMLTag)
                        .collect(toSet()));
    }
}
