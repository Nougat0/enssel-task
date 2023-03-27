package com.enssel.verbena.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestNougat0 is a Querydsl query type for TestNougat0
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestNougat0 extends EntityPathBase<TestNougat0> {

    private static final long serialVersionUID = 2077223801L;

    public static final QTestNougat0 testNougat0 = new QTestNougat0("testNougat0");

    public final StringPath pw = createString("pw");

    public final DateTimePath<java.time.LocalDateTime> regiDt = createDateTime("regiDt", java.time.LocalDateTime.class);

    public final StringPath regiUser = createString("regiUser");

    public final DateTimePath<java.time.LocalDateTime> updaDt = createDateTime("updaDt", java.time.LocalDateTime.class);

    public final StringPath updaUser = createString("updaUser");

    public final StringPath userId = createString("userId");

    public final StringPath userNm = createString("userNm");

    public final StringPath useYn = createString("useYn");

    public QTestNougat0(String variable) {
        super(TestNougat0.class, forVariable(variable));
    }

    public QTestNougat0(Path<? extends TestNougat0> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestNougat0(PathMetadata metadata) {
        super(TestNougat0.class, metadata);
    }

}

