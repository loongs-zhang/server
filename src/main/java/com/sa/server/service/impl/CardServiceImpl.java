package com.sa.server.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sa.server.dao.CardMapper;
import com.sa.server.pojo.Card;
import com.sa.server.service.CardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
* @author H
* @date 2019年4月21日 
* @description 与发行卡相关的业务
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService{

	private final CardMapper cardMapper;
	
	private final Sid sid;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean createCard(Card card) {
		card.setId(sid.nextShort());
		card.setDr(false);
		return cardMapper.insert(card) >=1 ? true:false;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateCardAid(Card card) {
		return cardMapper.updateByPrimaryKeySelective(card) >= 1 ? true:false;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateCardById(Card card) {
		Example example = new Example(Card.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", card.getId());
		return cardMapper.updateByExampleSelective(card, example) >= 1 ? true:false;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Card> queryCardsByUserId(String userId) {
		Example example = new Example(Card.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("dr", false);
		return cardMapper.selectByExample(example);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean deleteCardDr(String id) {
		Card card = new Card();
		card.setDr(true);
		Example example = new Example(Card.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		return cardMapper.updateByExampleSelective(card, example) >= 1 ? true:false;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean deleteCard(String id) {
		Example example = new Example(Card.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		return cardMapper.deleteByExample(example) >= 1 ? true:false;
	}
	
	
}
