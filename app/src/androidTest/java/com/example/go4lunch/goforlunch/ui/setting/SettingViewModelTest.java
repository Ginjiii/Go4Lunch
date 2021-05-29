package com.example.go4lunch.goforlunch.ui.setting;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SettingViewModelTest extends TestCase {

    private SettingViewModel settingViewModel;
    private Coworker coworker;
    private Context context;

    @Mock
    private CoworkerRepository coworkerRepository;
    @Mock
    private SaveDataRepository saveDataRepository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    public void setUp() throws Exception {
        coworker = new Coworker("uid", "name", "email", "photoUrl");
        when(coworkerRepository.getActualUser()).thenReturn(coworker);
        when(saveDataRepository.getNotificationSettings(coworker.getUid())).thenReturn(true);
        settingViewModel = new SettingViewModel(coworkerRepository,saveDataRepository);
        super.setUp();
    }

    @Test
    public void testNotificationStateChanged() {
        settingViewModel.notificationStateChanged(false,context);
        verify(saveDataRepository).saveNotificationSettings(false, coworker.getUid());
    }
}