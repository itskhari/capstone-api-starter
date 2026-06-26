package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile getByUserId(int userId) { return profileRepository.findByUserId(userId); }

    @Transactional
    public Profile updateProfile(int userId, Profile updatedProfile) {
        Profile existing = profileRepository.findByUserId(userId);
        if (existing == null) {
            throw new RuntimeException("Profile not found " + userId);
        }

        existing.setFirstName(updatedProfile.getFirstName());
        existing.setLastName(updatedProfile.getLastName());
        existing.setPhone(updatedProfile.getPhone());
        existing.setEmail(updatedProfile.getEmail());
        existing.setAddress(updatedProfile.getAddress());
        existing.setCity(updatedProfile.getCity());
        existing.setState(updatedProfile.getState());
        existing.setZip(updatedProfile.getZip());

        return profileRepository.save(existing);
    }
}
