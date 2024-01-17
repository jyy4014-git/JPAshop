package com.boardproject.jpashop.repository;

import com.boardproject.jpashop.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if (item.getId()==null){
            //id가 없는 것은 새로 객체를 만드는 것이다.
            em.persist(item);
        } else{
            //기존에 있던 것에 업데이트 하는 것
            em.merge(item);
        }
    }
    public Item fiondOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
