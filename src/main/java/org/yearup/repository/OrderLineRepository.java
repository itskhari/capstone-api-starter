package org.yearup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yearup.models.OrderLineItems;

public interface OrderLineRepository extends JpaRepository<OrderLineItems, Integer> {
}
