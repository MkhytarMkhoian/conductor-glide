# ConductorGlide
 Support Glide for Conductor library
 
 ## Sample
 
```java
public class GlideControllerSupport extends BaseGlideControllerSupport<GlideRequests> {

  public GlideControllerSupport(Controller controller) {
    super(controller);
  }

  @Override protected GlideRequests getGlideRequest(@NonNull ControllerLifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode) {
    Context context = App.getInstance().getApplicationContext();
    return new GlideRequests(Glide.get(context), lifecycle, requestManagerTreeNode, context);
  }
}
```

```java
public class HomeController extends Controller implements GlideProvider<GlideRequests>{

  private GlideControllerSupport glideControllerSupport = new GlideControllerSupport(this);

  @Override public GlideRequests getGlide() {
    return glideControllerSupport.getGlide();
  }

  @NonNull @Override protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

    getGlide().load("url")
      .centerCrop()
      .into(new SimpleTarget<Drawable>() {
        @Override public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

        }
      });

    return null;
  }
}
```
 
 ## License
```
Copyright 2018 Mkhytar Mkhoian, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
