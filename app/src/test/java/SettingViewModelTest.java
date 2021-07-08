import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;
import com.example.go4lunch.goforlunch.ui.setting.SettingViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettingViewModelTest {

    private SettingViewModel settingViewModel;
    private Coworker coworker;

    @Mock
    private CoworkerRepository coworkerRepository;
    @Mock
    private SaveDataRepository saveDataRepository;
    @Mock
    Context mockContext;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        coworker = new Coworker("uid", "name", "email", "urlPhoto");
        when(coworkerRepository.getActualUser()).thenReturn(coworker);
        settingViewModel = new SettingViewModel(coworkerRepository, saveDataRepository);
    }

    @Test
    public void userInfo_correct(){
        assertFalse(saveDataRepository.getNotificationSettings(coworker.getUid()));
    }

    @Test
    public void disableNotification_disable(){
        when(saveDataRepository.getNotificationSettings(coworker.getUid())).thenReturn(false);

        settingViewModel.notificationStateChanged(false, mockContext);
        assertFalse(settingViewModel.getStatusNotification(mockContext));
        verify(saveDataRepository).saveNotificationSettings(false, coworker.getUid());
    }

    @Test
    public void enableNotification_enable(){
        when(saveDataRepository.getNotificationSettings(coworker.getUid())).thenReturn(true);
        settingViewModel.notificationStateChanged(true,mockContext);
        assertTrue(settingViewModel.getStatusNotification(mockContext));
        verify(saveDataRepository).saveNotificationSettings(true, coworker.getUid());
    }
}
