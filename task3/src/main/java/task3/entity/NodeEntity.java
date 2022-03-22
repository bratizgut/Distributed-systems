package task3.entity;

import java.io.Serializable;
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

@Entity
@Table(name = "nodes")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class NodeEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "node_tag", joinColumns = {
        @JoinColumn(name = "node_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "id")
    })
    private Set<TagEntity> tags;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "_user")
    private String user;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "version")
    private Long version;

    @Column(name = "changeset")
    private Long changeSet;

    public static NodeEntity convertFromXMLNode(task3.models.generated.Node node) {
        return new NodeEntity().setId(node.getId().longValue())
                .setLat(node.getLat()).setLon(node.getLon())
                .setUser(node.getUser()).setUid(node.getUid().longValue())
                .setVisible(node.isVisible()).setVersion(node.getVersion().longValue())
                .setChangeSet(node.getChangeset().longValue())
                .setTags(node.getTag()
                        .stream()
                        .map(TagEntity::convertFromXMLTag)
                        .collect(toSet()));
    }
}
