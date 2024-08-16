package com.company.firstprojectspring.service.impl;

import com.company.firstprojectspring.dto.ApiResponse;
import com.company.firstprojectspring.dto.CardDto;
import com.company.firstprojectspring.module.Card;
import com.company.firstprojectspring.repository.CardRepository;
import com.company.firstprojectspring.service.CardService;
import com.company.firstprojectspring.service.mapper.CardMapper;
import com.company.firstprojectspring.service.mapper.UserMapper;
import com.company.firstprojectspring.service.validation.CardValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    protected final List<Card> cardList;
    private final UserMapper userMapper;
    private final CardMapper cardMapper;
    private final UserServiceImpl userService;
    private final CardValidation cardValidation;
    private final CardRepository cardRepository;



    @Override
    public ApiResponse<CardDto> createCard(CardDto dto) {
//        if (!this.userService.existByUserId(dto.getUserId())) {
//            return ApiResponse.<CardDto>builder()
//                    .code(-1)
//                    .message(String.format("User %d is not found ",dto.getUserId())).build();
//
//        }

//        List<ErrorDto> errors = this.cardValidation.validateCard(dto);
//
////        if(errorList.size()>0){}
//
//
//
//
//        if (!errors.isEmpty()) {
//            return ApiResponse.<CardDto>builder()
//                    .code(-2)
//                    .message("Validation Failed")
//                    .errorList(errors)
//                    .build();
//
//        }
//
//
//        ApiResponse<UserDto> apiResponse = this.userService.getUserById(card.getUserId());
//        Users users = this.userMapper.toEntity(apiResponse.getContent());
//        card.setHolderName(String.format("%s %s", users.getFirstname(), users.getLastname()));
        Card card = this.cardMapper.toEntity(dto);
        Card savedCard = this.cardRepository.save(card);

        return ApiResponse.<CardDto>builder()
                .success(true)
                .message("OK")
                .content(this.cardMapper.toDto(savedCard))
                .build();
    }


    @Override
    public ApiResponse<CardDto> getCardById(Integer cardId) {
        Optional<Card> optionalCard = this.cardRepository.findByCardIdAndDeletedAtIsNull(cardId);
        if (optionalCard.isEmpty()) {
            return  ApiResponse.<CardDto>builder()
                    .code(-2)
                    .message(String.format("Card %d is not found", cardId))
                    .build();
        }
        Card card = optionalCard.get();
        return ApiResponse.<CardDto>builder()
                .success(true)
                .message("OK")
                .content(this.cardMapper.toDto(card))
                .build();
    }


    @Override
    public ApiResponse<CardDto> updateCardById(Integer cardId, CardDto dto) {

//        List<ErrorDto> errors = this.cardValidation.validateCardWithUserId(dto);
//        if (!errors.isEmpty()) {
//            return ApiResponse.<CardDto>builder()
//                    .code(-2)
//                    .message("Validation Failed")
//                    .errorList(errors)
//                    .build();
//        }


        Optional<Card> optionalCard = this.cardRepository.findByCardIdAndDeletedAtIsNull(cardId);
        if (optionalCard.isEmpty()) {
            return ApiResponse.<CardDto>builder()
                    .code(-1)
                    .message(String.format("Card %d is not found", cardId))
                    .build();
        }
        Card card = optionalCard.get();
        Card updatedCard = this.cardMapper.updateAllFieldsCard(card, dto);
        Card savedCArd = this.cardRepository.save(updatedCard);
        return ApiResponse.<CardDto>builder()
                .success(true)
                .message("OK")
                .content(this.cardMapper.toDto(savedCArd))
                .build();
    }

    @Override
    public ApiResponse<CardDto> deleteCardById(Integer cardId) {

        Optional<Card> optionalCard = this.cardRepository.findByCardIdAndDeletedAtIsNull(cardId);
        if (optionalCard.isEmpty()) {
            return ApiResponse.<CardDto>builder()
                    .code(-1)
                    .message(String.format("Card %d is not found", cardId))
                    .build();
        }
        Card card = optionalCard.get();
        card.setDeletedAt(LocalDateTime.now());
        Card saved = this.cardRepository.save(card);

        return ApiResponse.<CardDto>builder()
                .success(true)
                .message("OK")
                .content(this.cardMapper.toDto(saved))
                .build();
    }

    @Override
    public ApiResponse<List<CardDto>> getAllCards() {
        List<Card> cardList = this.cardRepository.findAllByDeletedAtIsNull();
        if (cardList.isEmpty()) {
            return ApiResponse.<List<CardDto>>builder()
                    .code(-1)
                    .message("Card list is empty")
                    .build();
        }
        return ApiResponse.<List<CardDto>>builder()
                .success(true)
                .message("OK")
                .content(this.cardMapper.toDtoList(cardList))
                .build();
    }



    public List<CardDto> getAllCardsByUserId(Integer userId) {
        return this.cardMapper.toDtoList(
                this.cardRepository.findAllByUserIdAndDeletedAtIsNull(userId));
    }
}
