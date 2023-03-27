package com.enssel.verbena.api.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupByGridRows is a Querydsl query type for GroupByGridRows
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupByGridRows extends EntityPathBase<GroupByGridRows> {

    private static final long serialVersionUID = -1517272500L;

    public static final QGroupByGridRows groupByGridRows = new QGroupByGridRows("groupByGridRows");

    public final NumberPath<Long> accountSum = createNumber("accountSum", Long.class);

    public final StringPath regiDate = createString("regiDate");

    public QGroupByGridRows(String variable) {
        super(GroupByGridRows.class, forVariable(variable));
    }

    public QGroupByGridRows(Path<? extends GroupByGridRows> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupByGridRows(PathMetadata metadata) {
        super(GroupByGridRows.class, metadata);
    }

}

