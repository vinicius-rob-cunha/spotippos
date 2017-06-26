package br.com.spotippos.properties.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProperty is a Querydsl query type for Property
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProperty extends EntityPathBase<Property> {

    private static final long serialVersionUID = 373591943L;

    public static final QProperty property = new QProperty("property");

    public final NumberPath<Integer> baths = createNumber("baths", Integer.class);

    public final NumberPath<Integer> beds = createNumber("beds", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> idProperty = createNumber("idProperty", Long.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final ListPath<Province, EnumPath<Province>> provinces = this.<Province, EnumPath<Province>>createList("provinces", Province.class, EnumPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> squareMeters = createNumber("squareMeters", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> x = createNumber("x", Integer.class);

    public final NumberPath<Integer> y = createNumber("y", Integer.class);

    public QProperty(String variable) {
        super(Property.class, forVariable(variable));
    }

    public QProperty(Path<? extends Property> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProperty(PathMetadata<?> metadata) {
        super(Property.class, metadata);
    }

}

