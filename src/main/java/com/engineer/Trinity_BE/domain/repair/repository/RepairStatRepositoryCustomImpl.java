package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairStatRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairStatItem;
import com.engineer.Trinity_BE.domain.repair.enums.StatUnit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RepairStatRepositoryCustomImpl implements RepairStatRepositoryCustom{

    private final EntityManager entityManager;

    @Override
    public List<RepairStatItem> findStatistics(RepairStatRequest request, LocalDateTime from, StatUnit unit) {

        StringBuilder sql = new StringBuilder("""
               SELECT
               %s AS label,
               COUNT(r.id) AS count
               FROM repair r
               JOIN airplane a
               ON a.id = r.airplane_id
               WHERE 1 = 1
               """.formatted(getGroupExpression(unit)));
        Map<String, Object> params = new HashMap<>();

        if(from != null) {
            sql.append(" AND r.created_at >= :from ");
            params.put("from", from);
        }

        appendTypeCondition(sql, params, request);

        sql.append("""
                GROUP BY %s
                ORDER BY MIN(r.created_at)
                """.formatted(getGroupExpression(unit)));

        Query query = entityManager.createNativeQuery(String.valueOf(sql));

        params.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(row -> new RepairStatItem(
                        (String) row[0],
                        ((Number) row[1]).longValue()
                )).toList();
    }

    private String getGroupExpression(StatUnit unit) {
        return switch (unit) {
            case MONTH -> "DATE_FORMAT(r.created_at, '%Y-%m')";
            case WEEK -> "DATE_FORMAT(r.created_at, INTERVAL WEEKDAY(r.created_at) DAY, '%Y-%m-%d')";
        };
    }

    private void appendTypeCondition(
            StringBuilder sql,
            Map<String, Object> params,
            RepairStatRequest request
    ) {
        switch (request.type()) {
            case ALL -> {

            }

            case AIRPLANE_TYPE -> {
                sql.append("""
                        AND a.airplane_type_id = :airpalenTypeId
                        """);

                params.put(
                        "airplaneTypeId",
                        request.airplaneId()
                );
            }

            case AIRPLANE -> {
                sql.append("""
                        AND a.airplane_type_id = :airplaneTypeId
                        AND a.id = :airplaneId
                        """);
                params.put(
                        "airpalenTypeId",
                        request.airplaneTypeId()
                );
                params.put(
                        "airplaneId",
                        request.airplaneId()
                );
            }

            case CHAPTER -> {
                sql.append("""
                        AND a.airplane_type_id = :airplaneTypeId
                        AND a.id = :airplaneId
                        AND r.chapter = :chapter
                        """);
                params.put(
                        "airplaneTypeId",
                        request.airplaneTypeId()
                );
                params.put(
                        "airplaneId",
                        request.airplaneId()
                );

                params.put(
                        "chapter",
                        request.chapter()
                );
            }
        }
    }
}
