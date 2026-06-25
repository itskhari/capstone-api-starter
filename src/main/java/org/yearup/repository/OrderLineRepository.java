package org.yearup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.OrderLineItems;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineItems, Integer> {
}
