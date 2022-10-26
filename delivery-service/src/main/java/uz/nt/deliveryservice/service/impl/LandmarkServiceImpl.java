package uz.nt.deliveryservice.service.impl;

import uz.nt.deliveryservice.dto.LandmarkDto;
import uz.nt.deliveryservice.entity.Landmark;
import uz.nt.deliveryservice.repository.LandmarkRepository;
import uz.nt.deliveryservice.service.LandmarkService;
import uz.nt.deliveryservice.service.mapper.LandmarkMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;
import java.util.ResourceBundle;

@Service
@Slf4j
@RequiredArgsConstructor
public class LandmarkServiceImpl implements LandmarkService {

    private final LandmarkRepository landmarkRepository;

    @Override
    public ResponseDto<List<LandmarkDto>> getAllByCityId(Integer id) {
        try {
            List<Landmark> landmarks = landmarkRepository.findAllByCityId(id);

            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            if (landmarks.isEmpty())
                return ResponseDto.<List<LandmarkDto>>builder().code(404).success(false).message(bundle.getString("res.not_found")).build();

            return ResponseDto.<List<LandmarkDto>>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.found"))
                    .responseData(LandmarkMap.toDtoList(landmarks))
                    .build();

        } catch (Exception e) {
            log.error("Landmark Service GetAllByCityId method");
            return ResponseDto.<List<LandmarkDto>>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }
}
