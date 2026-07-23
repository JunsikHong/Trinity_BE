package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.dto.enums.RepairSortBy;
import com.engineer.Trinity_BE.domain.repair.dto.enums.RepairSortDirection;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairSearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RepairRepositoryCustomImpl implements RepairRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Long> findPageIds(Long airplaneId, RepairSearchRequest request, LocalDateTime cursorValue, Long cursorId, int size) {
        String sortColumn = request.sortBy() == RepairSortBy.CREATED_AT ? "r.createdAt" : "r.repairAt";
        boolean asc = request.sortDirection() == RepairSortDirection.ASC;
        String cmp = asc ? ">" : "<";
        String order = asc ? "asc" : "desc";

        StringBuilder jpql = new StringBuilder("""
                select distinct r.id
                from Repair r
                join r.repairLocationItems rli
                join rli.repairLocation rl
                join rl.repairChapter rc
                where r.airplane.id = :airplaneId
                  and r.deletedAt is null
                  and rl.isActive = true
                  and rc.isActive = true
                """);

        if(StringUtils.hasText(request.search())) {
            jpql.append(" and r.description like :search ");
        }

        if(request.chapterId() != null) {
            jpql.append(" and rc.id = :chapterId ");
        }

        if(request.startDate() != null) {
            jpql.append(" and r.repairAt >= :startDateTime ");
        }

        if(request.endDate() != null) {
            jpql.append(" and r.repairAt < :endDateTime ");
        }

        if(cursorValue != null && cursorId != null) {
            jpql.append(" and (%s %s :cursorValue or ($s = :cursorValue and r.id %s :cursorId)) ".formatted(sortColumn, cmp, sortColumn, cmp));
        }

        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
        query.setParameter("airplaneId", airplaneId);

        if(StringUtils.hasText(request.search())) {
            query.setParameter("search", "%" + request.search() + "%");
        }

        if(request.chapterId() != null) {
            query.setParameter("chapterId", request.chapterId());
        }

        if(request.startDate() != null) {
            query.setParameter("startDateTime", request.startDate().atStartOfDay());
        }

        if(request.endDate() != null) {
            query.setParameter("endDateTime", request.endDate().plusDays(1).atStartOfDay());
        }

        if(cursorValue != null && cursorId != null) {
            query.setParameter("cursorValue", cursorId);
            query.setParameter("cursorId", cursorId);
        }

        query.setMaxResults(size);

        return query.getResultList();
    }
}
