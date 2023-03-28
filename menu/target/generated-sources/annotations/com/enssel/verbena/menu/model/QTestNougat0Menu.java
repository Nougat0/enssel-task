package com.enssel.verbena.menu.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestNougat0Menu is a Querydsl query type for TestNougat0Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestNougat0Menu extends EntityPathBase<TestNougat0Menu> {

    private static final long serialVersionUID = -198192951L;

    public static final QTestNougat0Menu testNougat0Menu = new QTestNougat0Menu("testNougat0Menu");

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final StringPath menuNm = createString("menuNm");

    public final DateTimePath<java.time.LocalDateTime> regiDt = createDateTime("regiDt", java.time.LocalDateTime.class);

    public final StringPath regiUser = createString("regiUser");

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updaDt = createDateTime("updaDt", java.time.LocalDateTime.class);

    public final StringPath updaUser = createString("updaUser");

    public final NumberPath<Integer> uprMenuId = createNumber("uprMenuId", Integer.class);

    public final StringPath url = createString("url");

    public final StringPath useYn = createString("useYn");

    public QTestNougat0Menu(String variable) {
        super(TestNougat0Menu.class, forVariable(variable));
    }

    public QTestNougat0Menu(Path<? extends TestNougat0Menu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestNougat0Menu(PathMetadata metadata) {
        super(TestNougat0Menu.class, metadata);
    }

}

