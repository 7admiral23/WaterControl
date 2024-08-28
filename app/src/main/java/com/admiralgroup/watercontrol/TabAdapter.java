package com.admiralgroup.watercontrol;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Map;

public class TabAdapter extends FragmentPagerAdapter {
    private final Map<String, PermissionsResponse.Action> actions;

    public TabAdapter(FragmentManager fm, Map<String, PermissionsResponse.Action> actions) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.actions = actions;
    }

    @Override
    public Fragment getItem(int position) {
        String groupKey = (String) actions.keySet().toArray()[position];
        PermissionsResponse.Action action = actions.get(groupKey);
        return CommandFragment.newInstance(action);
    }

    @Override
    public int getCount() {
        return actions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String groupKey = (String) actions.keySet().toArray()[position];
        return actions.get(groupKey).getLabel();
    }
}
