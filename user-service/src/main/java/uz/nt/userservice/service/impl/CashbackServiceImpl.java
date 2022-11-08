package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.userservice.repository.UserRepository;
import uz.nt.userservice.service.CashbackService;
import uz.nt.userservice.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class CashbackServiceImpl implements CashbackService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

//    @Override
//    public ResponseDto<Integer> getCashbackCardId(Integer id) {
//        try {
//            Optional<User> user = userRepository.findById(id);
//            if (user.isPresent()) {
//                UserDto userDto = userMapper.toDto(user.get());
//                Integer cashbackId = userDto.getCashbackCardId();
//                return ResponseDto.<Integer>builder().message("OK")
//                        .code(0)
//                        .success(true)
//                        .responseData(cashbackId)
//                        .build();
//            }
//            return ResponseDto.<Integer>builder().message("Not found")
//                    .code(1)
//                    .success(false)
//                    .responseData(null)
//                    .build();
//
//        } catch (Exception e) {
//            return ResponseDto.<Integer>builder().message(e.getMessage())
//                    .code(-1)
//                    .success(false)
//                    .responseData(null)
//                    .build();
//        }
//    }


}
