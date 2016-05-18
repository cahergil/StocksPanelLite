package com.carlos.capstone.adapters;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by Carlos on 28/12/2015.
 */
public class Stats implements ParentListItem {
    private List<StatsChild> mChildrenList;
    private String nombreGrupo;

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

//    @Override
//    public List<Object> getChildObjectList() {
//        return mChildrenList;
//    }
//
//    @Override
//    public void setChildObjectList(List<Object> list) {
//        mChildrenList = list;
//
//    }

    public void setChildObjectList(List<StatsChild> list) {
          mChildrenList=list;
    }
    @Override
    public List<StatsChild> getChildItemList() {
        return mChildrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }
}
