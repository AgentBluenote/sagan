package sagan.team.support;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sagan.team.MemberProfile;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTests {
    @Mock
    private TeamRepository teamRepository;

    private TeamService service;

    @Before
    public void setup() {
        service = new TeamService(teamRepository);
    }

    @Test
    public void updateMemberProfileUpdatesAvatarUrlFromGravatarEmail() {
        MemberProfile savedProfile = new MemberProfile();
        given(teamRepository.findById(1234L)).willReturn(savedProfile);

        MemberProfile updatedProfile = new MemberProfile();
        updatedProfile.setGravatarEmail("test@example.com");
        service.updateMemberProfile(1234L, updatedProfile);

        assertThat(savedProfile.getGravatarEmail(), equalTo("test@example.com"));
        assertThat(savedProfile.getAvatarUrl(), equalTo("https://gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0"));
    }

    @Test
    public void updateMemberProfileDoesNotUpdateAvatarUrlIfGravatarEmailIsEmpty() {
        MemberProfile savedProfile = new MemberProfile();
        savedProfile.setAvatarUrl("http://example.com/image.png");
        given(teamRepository.findById(1234L)).willReturn(savedProfile);

        MemberProfile updatedProfile = new MemberProfile();
        updatedProfile.setGravatarEmail("");
        service.updateMemberProfile(1234L, updatedProfile);

        assertThat(savedProfile.getAvatarUrl(), equalTo("http://example.com/image.png"));
    }
}
