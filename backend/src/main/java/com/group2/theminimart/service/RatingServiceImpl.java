package com.group2.theminimart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.group2.theminimart.dto.RatingResponseDto;
import com.group2.theminimart.entity.Rating;
import com.group2.theminimart.exception.RatingNotFoundException;
import com.group2.theminimart.mapper.RatingMapper;
import com.group2.theminimart.repository.RatingRepository;

@Primary
@Service
public class RatingServiceImpl implements RatingService {

  private RatingRepository ratingRepository;

  public RatingServiceImpl(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  @Override
  public List<RatingResponseDto> getRatings() {
    List<Rating> allRating = ratingRepository.findAll();

    return allRating.stream().map((rating) -> RatingMapper.RatingtoDto(rating)).collect(Collectors.toList());
  }

  @Override
  public RatingResponseDto getRating(Long id) {
    return RatingMapper.RatingtoDto(ratingRepository.findById(id).orElseThrow(() -> new RatingNotFoundException()));
  }

  @Override
  public RatingResponseDto updateRating(Long id, Rating rating) {
    Rating updatedRating = ratingRepository.findById(id).orElseThrow(() -> new RatingNotFoundException());
    updatedRating.setRate(rating.getRate());

    return RatingMapper.RatingtoDto(ratingRepository.save(updatedRating));

  }

  @Override
  public void deleteRating(Long id) {
    Rating existingRating = ratingRepository.findById(id).orElseThrow(() -> new RatingNotFoundException());

    ratingRepository.delete(existingRating);

  }

}
